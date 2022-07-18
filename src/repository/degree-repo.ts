import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Degree } from '../entity'

export class DegreeRepository {

  repo: Repository<Degree>

  constructor() {
    this.repo = AppDataSource.getRepository(Degree);
  }

  //#######################################################################
  async AddDegree(name: string): Promise<Degree> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Unable to Add Degree')

    }
  }


  //#######################################################################
  async ListDegree(): Promise<Degree[]> {

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

