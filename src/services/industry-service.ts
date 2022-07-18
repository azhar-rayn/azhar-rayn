// All Business logic will be here

import { Industry } from "../entity";
import { IndustryRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class IndustryService {

  private repository: IndustryRepository

  constructor() {

    this.repository = new IndustryRepository();

  }


  //#######################################################################
  async AddIndustry(name: string): Promise<Industry> {

    try {

      return await this.repository.AddIndustry(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListIndustry(): Promise<Industry[]> {

    try {

      return await this.repository.ListIndustry();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}