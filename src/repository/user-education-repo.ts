import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Education } from '../entity'


export class UserEducationRepository {

  education: Repository<Education>

  constructor() {
    this.education = AppDataSource.getRepository(Education);

  }

  //#######################################################################
  async GetEducation(educationID: string): Promise<Education> {

    try {

      const result = await this.education.findOne({ where: { ID: educationID } });

      return result as Education

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteEducation(educationID: string): Promise<any> {

    try {

      const result = await this.education
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: educationID })
        .execute();

      return result.affected;

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateEducation(educationID: string, degreeID: string, categoryID: string, fieldID: string, instituationID: string, year_of_completion: number): Promise<Education> {

    try {

      const result = await this.education
        .createQueryBuilder()
        .update()
        .set({
          degreeID,
          fieldID,
          instituationID,
          year_of_completion
        })
        .returning("*")
        .where("ID = :ID", { ID: educationID })
        .execute()

      return result.raw

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

}

