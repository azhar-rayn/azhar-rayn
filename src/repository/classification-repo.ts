import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Classification } from '../entity'

export class ClassificationRepository {

  repo: Repository<Classification>

  constructor() {
    this.repo = AppDataSource.getRepository(Classification);
  }

  //#######################################################################
  async AddClassification(name: string): Promise<Classification> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async ListClassification(): Promise<Classification[]> {

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

