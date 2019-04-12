package org.softuni.jewelleryshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.entities.Role;
import org.softuni.jewelleryshop.domain.models.service.RoleServiceModel;
import org.softuni.jewelleryshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role(GlobalConstants.ROLE_USER));
            this.roleRepository.saveAndFlush(new Role(GlobalConstants.ROLE_MODERATOR));
            this.roleRepository.saveAndFlush(new Role(GlobalConstants.ROLE_ADMIN));
            this.roleRepository.saveAndFlush(new Role(GlobalConstants.ROLE_ROOT));
        }
    }

//    @Override
//    public void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers) {
//        if (numberOfUsers == 0) {
//            userServiceModel
//                    .setAuthorities(this.roleRepository
//                            .findAll()
//                            .stream()
//                            .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
//                            .collect(Collectors.toSet()));
//        }
//    }


    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper
                .map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
