// All Business logic will be here

import { Candidate_Experience } from "../entity";
import { UserExperienceRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class UserExperienceService {

  private repository: UserExperienceRepository

  constructor() {

    this.repository = new UserExperienceRepository();

  }


  //#######################################################################
  async GetExperience(experienceID: string): Promise<Candidate_Experience> {

    try {

      return await this.repository.GetExperience(experienceID);

    }
    catch (err) {
     
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteExperience(experienceID: string): Promise<Candidate_Experience> {

    try {

      return await this.repository.DeleteExperience(experienceID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
    
    }

  }

  //#######################################################################
  async UpdateExperience(experienceID: string, title: string, categoryID: string, companyName: string, city: string, experience: number): Promise<Candidate_Experience> {

    try {

      return await this.repository.UpdateExperience(experienceID, title, categoryID, companyName, city, experience);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}