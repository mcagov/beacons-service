package uk.gov.mca.beacons.api.gateways;

public interface CrudGateway<T, TCreate> {
  T create(TCreate request);
}
