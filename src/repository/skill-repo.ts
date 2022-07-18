import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Skill } from '../entity'

export class SkillRepository {

  repo: Repository<Skill>

  constructor() {
    this.repo = AppDataSource.getRepository(Skill);
  }

  //#######################################################################
  async AddSkill(name: string): Promise<Skill> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Unable to Add Skill')

    }
  }

  //#######################################################################
  async ListSkill(): Promise<Skill[]> {

    try {

      const res = await this.repo.find({
        order: {
          name: "ASC"
        }
      })

      return res
    }
    catch (err) {
     
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }
}

