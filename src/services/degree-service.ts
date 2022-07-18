// All Business logic will be here

import { Degree } from "../entity";
import { DegreeRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class DegreeService {

  private repository: DegreeRepository

  constructor() {

    this.repository = new DegreeRepository();

  }


  //#######################################################################
  async AddDegree(name: string): Promise<Degree> {

    try {

      return await this.repository.AddDegree(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListDegree(): Promise<Degree[]> {

    try {

      return await this.repository.ListDegree();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}