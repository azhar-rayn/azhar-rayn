// All Business logic will be here

import { Education_Field } from "../entity";
import { EducationFieldRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class EducationFieldService {

  private repository: EducationFieldRepository

  constructor() {

    this.repository = new EducationFieldRepository();

  }


  //#######################################################################
  async AddEducationField(name: string): Promise<Education_Field> {

    try {

      return await this.repository.AddEducationField(name);

    } catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListEducationField(): Promise<Education_Field[]> {

    try {

      return await this.repository.ListEducationField();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}