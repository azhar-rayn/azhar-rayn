import { Certificate, Degree, Education } from '../entity';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Candidate_Experience } from '../entity'
import { APIError, STATUS_CODES } from '../utils/app-errors';


export class UserExperienceRepository {

  experience: Repository<Candidate_Experience>

  constructor() {
    this.experience = AppDataSource.getRepository(Candidate_Experience);

  }

  //#######################################################################
  async GetExperience(experienceID: string): Promise<Candidate_Experience> {

    try {

      const result = await this.experience.findOne({ where: { ID: experienceID } });

      return result as Candidate_Experience

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async DeleteExperience(experienceID: string): Promise<Candidate_Experience> {

    try {

      const result = await this.experience
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: experienceID })
        .execute();

      return result.raw as Candidate_Experience;

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
    
    }

  }

  //#######################################################################
  async UpdateExperience(experienceID: string, title: string, categoryID: string, companyName: string, city: string, experience: number): Promise<Candidate_Experience> {

    try {

      const result = await this.experience
        .createQueryBuilder()
        .update()
        .set({
          title,
          categoryID,
          city,
          companyName,
          experience
        })
        .returning("*")
        .where("ID = :ID", { ID: experienceID })
        .execute()

      return result.raw

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

}

