import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Instituation } from '../entity'

export class InstituationRepository {

  repo: Repository<Instituation>

  constructor() {
    this.repo = AppDataSource.getRepository(Instituation);
  }

  //#######################################################################
  async AddInstituation(name: string): Promise<Instituation> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Unable to Add Institution')

    }
  }


  //#######################################################################
  async ListInstituation(): Promise<Instituation[]> {

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

