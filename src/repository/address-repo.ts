import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Address } from '../entity'

export class AddressRepository {

  repo: Repository<Address>

  constructor() {
    this.repo = AppDataSource.getRepository(Address);
  }


  //#######################################################################
  async AddAddress(address: Address): Promise<Address> {

    const { city, country, house, lat, long, street } = address

    try {

      const res = await this.repo.save({ city, country, house, lat, long, street });
      return res;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }


  //#######################################################################
  async UpdateAddress(address: Address): Promise<Address> {

    const { city, country, house, lat, long, street, ID } = address

    try {
      const result = await this.repo
        .createQueryBuilder()
        .update()
        .set({
          city,
          country,
          house,
          lat,
          long,
          street
        })
        .returning("*")
        .where("ID = :ID", { ID })
        .execute()

      return result.raw
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

}

