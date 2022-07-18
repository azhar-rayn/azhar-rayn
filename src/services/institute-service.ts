// All Business logic will be here

import { Instituation } from "../entity";
import { InstituationRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class InstituationService {

  private repository: InstituationRepository

  constructor() {

    this.repository = new InstituationRepository();

  }


  //#######################################################################
  async AddInstituation(name: string): Promise<Instituation> {

    try {

      return await this.repository.AddInstituation(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListInstituation(): Promise<Instituation[]> {

    try {

      return await this.repository.ListInstituation();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}