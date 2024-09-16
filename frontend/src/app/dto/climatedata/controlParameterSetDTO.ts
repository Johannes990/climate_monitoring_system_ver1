export interface ControlParameterSetDTO {
    controlParameterSetId?: number; // marked as optional with the ? so that I can use it both to send and retrieve
    tempNorm: number;
    tempTolerance: number;
    relHumidityNorm: number;
    relHumidityTolerance: number;
}