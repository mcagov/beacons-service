package uk.gov.mca.beacons.api.gateways;

public interface CrudGateway<T, TCreate> {
  T create(TCreate request);
  T read(T request);
  T update(T request);
  T delete(T request);
}
