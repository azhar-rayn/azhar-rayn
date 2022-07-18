// All Business logic will be here

import { Skill } from "../entity";
import { SkillRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class SkillService {

  private repository: SkillRepository

  constructor() {

    this.repository = new SkillRepository();

  }


  //#######################################################################
  async AddSkill(name: string): Promise<Skill> {

    try {

      return await this.repository.AddSkill(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListSkill(): Promise<Skill[]> {

    try {

      return await this.repository.ListSkill();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}