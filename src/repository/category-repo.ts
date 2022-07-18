import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Category } from '../entity'

export class CategoryRepository {

  repo: Repository<Category>

  constructor() {
    this.repo = AppDataSource.getRepository(Category);
  }

  //#######################################################################
  async AddCategory(name: string): Promise<Category> {


    try {

      const res = await this.repo.save({ name });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async ListCategory(): Promise<Category[]> {

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

