// All Business logic will be here

import { UserSkill } from "../entity";
import { UserSkillRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class UserSkillService {

  private repository: UserSkillRepository

  constructor() {

    this.repository = new UserSkillRepository();

  }


  //#######################################################################
  async GetSkill(skillID: string): Promise<UserSkill> {

    try {

      return await this.repository.GetUserSkill(skillID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteSkill(skillID: string): Promise<UserSkill> {

    try {

      return await this.repository.DeleteUserSkill(skillID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async UpdateUserSkill(skillID: string, skill: string, level: string): Promise<UserSkill> {

    try {

      return await this.repository.UpdateUserSkill(skillID, skill, level);

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}