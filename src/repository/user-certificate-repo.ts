import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Certificate } from '../entity'


export class UserCertificateRepository {

  repo: Repository<Certificate>

  constructor() {
    this.repo = AppDataSource.getRepository(Certificate);

  }

  //#######################################################################
  async GetCertificate(certificateID: string): Promise<Certificate> {

    try {

      const result = await this.repo.findOne({ where: { ID: certificateID } });

      return result as Certificate

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteCertificate(certificateID: string): Promise<Certificate> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .delete()
        .where("ID = :ID", { ID: certificateID })
        .execute();

      return result.raw;

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateCertificate(certificateID: string, topic: string, type: string, instituation: string, date: Date): Promise<Certificate> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .update()
        .set({
          date,
          instituation,
          topic,
          type
        })
        .returning("*")
        .where("ID = :ID", { ID: certificateID })
        .execute()

      return result.raw

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

}

