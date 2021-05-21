package uk.gov.mca.beacons.service.hateoas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BeaconRolesService {

  public List<String> getUserRoles() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) return new ArrayList<>();

    return authentication
      .getAuthorities()
      .stream()
      .map(role -> role.toString())
      .collect(Collectors.toList());
  }
}
