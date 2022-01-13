import { IOwner } from "../../entities/IOwner";
import { IUse } from "../../entities/IUse";
import { IEmergencyContact } from "../../entities/IEmergencyContact";

export interface IBeaconResponse {
  id: string;
  hexId: string;
  status?: string;
  beaconType?: string;
  manufacturer?: string;
  createdDate?: string;
  lastModifiedDate?: string;
  model?: string;
  manufacturerSerialNumber?: string;
  chkCode?: string;
  mti?: string;
  svdr?: boolean;
  csta?: string;
  protocol?: string;
  coding?: string;
  batteryExpiryDate?: string;
  lastServicedDate?: string;
  referenceNumber: string;
  owner?: OwnerRegistrationResponse;
  uses?: UseRegistrationResponse[];
  emergencyContacts?: EmergencyContactRegistrationResponse[];
}

type OwnerRegistrationResponse = IOwner;

type UseRegistrationResponse = IUse;

type EmergencyContactRegistrationResponse = IEmergencyContact;
