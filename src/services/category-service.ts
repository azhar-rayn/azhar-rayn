// All Business logic will be here

import { Category } from "../entity";
import { CategoryRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class CategoryService {

  private repository: CategoryRepository

  constructor() {

    this.repository = new CategoryRepository();

  }


  //#######################################################################
  async AddCategory(name: string): Promise<Category> {

    try {

      return await this.repository.AddCategory(name);

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async ListCategory(): Promise<Category[]> {

    try {

      return await this.repository.ListCategory();

    } catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


}