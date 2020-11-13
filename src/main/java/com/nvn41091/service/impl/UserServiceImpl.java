package com.nvn41091.service.impl;

import com.nvn41091.domain.CompanyUser;
import com.nvn41091.domain.User;
import com.nvn41091.domain.UserRole;
import com.nvn41091.repository.*;
import com.nvn41091.security.SecurityUtils;
import com.nvn41091.service.UserService;
import com.nvn41091.service.dto.ResponseJwtDTO;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.mapper.UserMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.JwtTokenUtils;
import com.nvn41091.utils.Translator;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.security.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder encoder;

    private final UserMapper userMapper;

    private final UserRepository repository;

    private final UserRoleRepository userRoleRepository;

    private final ModuleRepository moduleRepository;

    private final CompanyUserRepository companyUserRepository;

    private final JavaMailSender emailSender;

    private final JwtTokenUtils jwtTokenUtils;

    private final CompanyRepository companyRepository;

    public UserServiceImpl(PasswordEncoder encoder, UserMapper userMapper, UserRepository repository,
                           UserRoleRepository userRoleRepository, ModuleRepository moduleRepository,
                           CompanyUserRepository companyUserRepository, JavaMailSender javaMailSender,
                           JwtTokenUtils jwtTokenUtils, CompanyRepository companyRepository) {
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
        this.moduleRepository = moduleRepository;
        this.companyUserRepository = companyUserRepository;
        this.emailSender = javaMailSender;
        this.jwtTokenUtils = jwtTokenUtils;
        this.companyRepository = companyRepository;
    }

    @Override
    public User saveNoLogin(UserDTO userDTO, HttpServletRequest request) {
        userDTO.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));
        userDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        String lang = request.getHeader("Accept-Language").split(",")[0];
        userDTO.setLangKey(lang);
        userDTO.setStatus(true);
        userDTO.setResetKey(RandomUtil.generateResetKey());
        validateEmailAndUsername(userDTO);
        User user = userMapper.toEntity(userDTO);
        return repository.save(user);
    }

    public void validateEmailAndUsername(UserDTO userDTO) {
        if (repository.findAllByUserNameAndIdNotEqual(userDTO.getUserName(), userDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("register.existUsername"), "user", "existUsername");
        }
        if (StringUtils.isNoneEmpty(userDTO.getEmail())) {
            if (repository.findAllByEmailAndIdNotEqual(userDTO.getEmail(), userDTO.getId()).size() > 0) {
                throw new BadRequestAlertException(Translator.toLocale("register.existEmail"), "user", "existEmail");
            }
        }
    }

    @Override
    public User saveToLogin(UserDTO userDTO) {
        log.debug("Request to save user : {}", userDTO);
        if (userDTO.getId() == null) {
            // Validate truong hop them moi
            userDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            userDTO.setCreateDate(Timestamp.from(Instant.now()));
        } else {
            // Validate truong hop cap nhat
            List<User> searchExist = repository.findAllById(userDTO.getId());
            if (searchExist.size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.user.notExist"), "user", "user.notExist");
            }
            userDTO.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            userDTO.setLastModifiedDate(Timestamp.from(Instant.now()));
        }
        validateEmailAndUsername(userDTO);
        User res = repository.save(userMapper.toEntity(userDTO));
        UserDTO currentLogin = SecurityUtils.getCurrentUser().get();
        List<UserRole> selected = new ArrayList<>();
        if (StringUtils.isNoneEmpty(userDTO.getLstRole())) {
            String[] lst = userDTO.getLstRole().split(",");
            for (String s : lst) {
                UserRole userRole = new UserRole();
                userRole.setUserId(res.getId());
                userRole.setRoleId(DataUtil.safeToLong(s));
                userRole.setCompanyId(currentLogin.getCompanyId());
                userRole.setUpdateTime(Instant.now());
                selected.add(userRole);
            }
        }
        UserDTO currentUser = SecurityUtils.getCurrentUser().get();
        List<UserRole> origin = userRoleRepository.getAllByUserIdAndCompanyId(res.getId(), currentUser.getCompanyId());
        Iterator<UserRole> i = origin.listIterator();
        while (i.hasNext()) {
            UserRole nextOrigin = i.next();
            boolean isUncheck = true;
            Iterator<UserRole> j = selected.listIterator();
            while (j.hasNext()) {
                UserRole nextSelected = j.next();
                if (nextOrigin.getRoleId().equals(nextSelected.getRoleId()) &&
                        nextOrigin.getUserId().equals(nextSelected.getUserId())) {
                    j.remove();
                    isUncheck = false;
                    break;
                }
            }
            if (!isUncheck) {
                i.remove();
            }
        }
        userRoleRepository.deleteInBatch(origin);
        userRoleRepository.saveAll(selected);
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUserId(res.getId());
        companyUser.setCompanyId(currentLogin.getCompanyId());
        companyUser.setUpdateTime(Instant.now());
        companyUserRepository.save(companyUser);
        return res;
    }

    @Override
    public void delete(User user) {
        UserDTO current = SecurityUtils.getCurrentUser().get();
        if (companyRepository.findAllByIdAndCreateBy(current.getCompanyId(), user.getId()) != null) {
            throw new BadRequestAlertException(Translator.toLocale("error.user.userCreate"), "user", "user.userCreate");
        }
        if (repository.findAllById(user.getId()).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.user.notExist"), "user", "user.notExist");
        }
        this.userRoleRepository.deleteAllByUserIdAndCompanyId(user.getId(), current.getCompanyId());
        this.companyUserRepository.deleteByUserIdAndCompanyId(user.getId(), current.getCompanyId());
    }

    @Override
    public Page<User> doSearch(User user, Pageable pageable) {
        UserDTO currentUser = SecurityUtils.getCurrentUser().get();
        Page<User> rs = repository.querySearchUser(DataUtil.makeLikeParam(user.getUserName()),
                DataUtil.makeLikeParam(user.getFullName()),
                DataUtil.makeLikeParam(user.getEmail()),
                user.getStatus(),
                currentUser.getCompanyId(),
                pageable);
        return new PageImpl<>(rs.getContent(), pageable, rs.getTotalElements());
    }

    @Override
    public UserDTO getUserInfo() {
        UserDTO userDTO = SecurityUtils.getCurrentUser().get();
        userDTO.setRoles(userRoleRepository.getUserRole(userDTO.getId(), userDTO.getCompanyId())
                .stream()
                .map(objects -> DataUtil.safeToString(objects[0]) + "#" + DataUtil.safeToString(objects[1]))
                .collect(Collectors.toList()));
        userDTO.setMenus(moduleRepository.findAllMenuByUserId(userDTO.getId(), userDTO.getCompanyId()));
        return userDTO;
    }

    @Override
    public void requestEmail(UserDTO userDTO) {
        if (repository.findAllByEmail(userDTO.getEmail()).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.requestPassword.emailNotFound"), "requestPassword", "requestPassword.emailNotFound");
        }
        String resetKey = RandomUtil.generateResetKey();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("nvn41091@gmail.com");
        mailMessage.setTo(userDTO.getEmail());
        mailMessage.setSubject("Ahihi đồ ngốc");
        mailMessage.setText(resetKey);
        emailSender.send(mailMessage);
        repository.updateResetKeyByEmail(resetKey, userDTO.getEmail(), Timestamp.from(Instant.now()));
        log.info(">>>>>>>> Send message success");
    }

    @Override
    public User requestPassword(UserDTO userDTO) {
        User result = repository.findAllByEmailAndResetKeyAndResetDate(userDTO.getEmail(), userDTO.getResetKey());
        if (result == null) {
            throw new BadRequestAlertException(Translator.toLocale("error.requestPassword.resetKeyNotCompare"), "requestPassword", "requestPassword.resetKeyNotCompare");
        }
        return result;
    }

    @Override
    public UserDTO resetPassword(UserDTO userDTO) {
        UserDTO user = SecurityUtils.getCurrentUser().get();
        if (userDTO.getResetKey() != null) {
            List<User> findLst = repository.findAllByIdAndResetKey(user.getId(), userDTO.getResetKey());
            if (findLst.size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.requestPassword.resetKeyNotCompare"), "resetPassword", "requestPassword.resetKeyNotCompare");
            }
        } else {
            if (!encoder.matches(userDTO.getOldPassword(), user.getPasswordHash())) {
                throw new BadRequestAlertException(Translator.toLocale("error.resetPassword.notCompareOldPassword"), "resetPassword", "resetPassword.notCompareOldPassword");
            }
        }
        user.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));
        repository.updatePasswordById(user.getPasswordHash(), user.getId());
        return user;
    }

    @Override
    public List<ResponseJwtDTO> createToken(UserDTO user) {
        List<ResponseJwtDTO> lstCompany = repository.getAllCompanyByUserName(user.getUserName());
        List<ResponseJwtDTO> result = lstCompany.stream().map(res -> {
            final String token = jwtTokenUtils.generateToken(res.getUsername() + "|" + res.getCompanyId());
            res.setToken(token);
            return res;
        }).collect(Collectors.toList());
        if (result.size() == 0) {
            ResponseJwtDTO selfToken = new ResponseJwtDTO();
            selfToken.setToken(jwtTokenUtils.generateToken(user.getUserName()));
            result.add(selfToken);
        }
        return result;
    }
}
