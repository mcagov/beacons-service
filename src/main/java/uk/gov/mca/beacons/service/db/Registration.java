package uk.gov.mca.beacons.service.db;

import java.util.List;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Registration {

    @Valid
    private List<Beacon> beacons;
}
