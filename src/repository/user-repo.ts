import { APIError, STATUS_CODES } from '../utils/app-errors';
import { AppDataSource } from '../data-source'
import { User } from '../entity'

export class UserRepository {

  //#######################################################################
  async CreateUser({ email, password, phone, source }: User): Promise<User> {
    const userRepo = AppDataSource.getRepository(User);
    try {
      const res = await userRepo.save({ email, password, phone, source });
      return res;
    }
    catch (err) {
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, "Bad Request")
    }
  }
}

