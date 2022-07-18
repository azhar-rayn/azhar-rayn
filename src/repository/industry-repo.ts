import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Industry } from '../entity'

export class IndustryRepository {

  repo: Repository<Industry>

  constructor() {
    this.repo = AppDataSource.getRepository(Industry);
  }

  //#######################################################################
  async AddIndustry(name: string): Promise<Industry> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  
  //#######################################################################
  async ListIndustry(): Promise<Industry[]> {

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

