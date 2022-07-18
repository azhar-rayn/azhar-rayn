import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Education_Field } from '../entity'

export class EducationFieldRepository {

  repo: Repository<Education_Field>

  constructor() {
    this.repo = AppDataSource.getRepository(Education_Field);
  }

  //#######################################################################
  async AddEducationField(name: string): Promise<Education_Field> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async ListEducationField(): Promise<Education_Field[]> {

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

