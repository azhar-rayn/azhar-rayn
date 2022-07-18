// All Business logic will be here

import { Classification } from "../entity";
import { ClassificationRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class ClassificationService {

  private repository: ClassificationRepository

  constructor() {

    this.repository = new ClassificationRepository();

  }


  //#######################################################################
  async AddClassification(name: string): Promise<Classification> {

    try {

      return await this.repository.AddClassification(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListClassification(): Promise<Classification[]> {

    try {

      return await this.repository.ListClassification();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}