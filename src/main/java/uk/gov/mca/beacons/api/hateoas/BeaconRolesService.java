package uk.gov.mca.beacons.api.hateoas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BeaconRolesService {

    public List<SupportedPermissions> getUserRoles() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return new ArrayList<>();

        return authentication
                .getAuthorities()
                .stream()
                .map(this::SupportedPermissionsFromString)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SupportedPermissions SupportedPermissionsFromString(
            GrantedAuthority role
    ) {
        try {
            return SupportedPermissions.valueOf(role.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public enum SupportedPermissions {
        APPROLE_UPDATE_RECORDS,
    }
}
