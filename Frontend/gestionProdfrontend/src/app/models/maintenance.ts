import { Machine } from "./machine";
import { Technicien } from "./technicien";

export interface Maintenance {
  id?: number;
 machineId: number;
  technicienId: number;
  date: string;
  type: string;
}