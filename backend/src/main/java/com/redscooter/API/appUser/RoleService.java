package com.redscooter.API.appUser;

import com.redscooter.API.common.BaseService;
import com.redscooter.exceptions.api.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<Role> {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super(roleRepository, "Role");
        this.roleRepository = roleRepository;
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> {
            throw buildResourceNotFoundException("name", name);
        });
    }

    @Override
    public Role save(Role role) {
        if (existsByName(role.getName()))
            throw new ResourceAlreadyExistsException("Role", "name", role.getName());
        return super.save(role);
    }
}
