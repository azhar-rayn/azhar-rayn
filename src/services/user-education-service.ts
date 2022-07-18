// All Business logic will be here

import { Education } from "../entity";
import { UserEducationRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class UserEducationService {

  private repository: UserEducationRepository

  constructor() {

    this.repository = new UserEducationRepository();

  }


  //#######################################################################
  async GetEducation(educationID: string): Promise<Education> {

    try {

      return await this.repository.GetEducation(educationID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteEducation(educationID: string): Promise<Education> {

    try {

      return await this.repository.DeleteEducation(educationID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateEducation(educationID: string, degreeID: string, categoryID: string, fieldID: string, instituationID: string, year_of_completion: number): Promise<Education> {

    try {

      return await this.repository.UpdateEducation(educationID, degreeID, categoryID, fieldID, instituationID, year_of_completion);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }


}