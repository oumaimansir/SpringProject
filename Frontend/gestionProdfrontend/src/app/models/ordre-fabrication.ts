export interface OrdreFabrication {
  id?: number;
  produitId: number;
  quantite: number;
  date: string;
  machineId: number;
  machineNom?: string;
  statut: string;
}