import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { UserSkill } from '../entity'


export class UserSkillRepository {

  repo: Repository<UserSkill>

  constructor() {
    this.repo = AppDataSource.getRepository(UserSkill);

  }

  //#######################################################################
  async GetUserSkill(skillID: string): Promise<UserSkill> {

    try {

      const result = await this.repo.findOne({ where: { ID: skillID } });

      return result as UserSkill

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteUserSkill(skillID: string): Promise<any> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: skillID })
        .execute();

      return result.affected;

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateUserSkill(skillID: string, skill: string, level: string): Promise<UserSkill> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .update()
        .set({
          skillID: skill,
          level
        })
        .returning("*")
        .where("ID = :ID", { ID: skillID })
        .execute()

      return result.raw

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

}

