import { APIError, STATUS_CODES } from '../utils/app-errors';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Company } from '../entity'


export class CompanyRepository {

  repo: Repository<Company>

  constructor() {
    this.repo = AppDataSource.getRepository(Company);

  }

  //#######################################################################
  async AddCompany(
    name: string,
    industryID: string,
    addressID: string,
    email: string,
    contactPersonName: string,
    jobDesignation: string,
    phone: number,
    logo: string
  ): Promise<Company> {

    try {

      const result = await this.repo.save({
        name,
        logo,
        phone,
        email,
        jobDesignation,
        contactPersonName,
        addressID,
        industryID
      })

      return result

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateCompany(
    companyID: string,
    name: string,
    industryID: string,
    email: string,
    contactPersonName: string,
    jobDesignation: string,
    phone: number,
    logo: string
  ): Promise<Company> {

    try {

      const result = await this.repo
        .createQueryBuilder()
        .update()
        .set({
          name,
          logo,
          phone,
          email,
          jobDesignation,
          contactPersonName,
          industryID
        })
        .returning("*")
        .where("ID = :ID", { ID: companyID })
        .execute()

      return result.raw

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetCompany(companyID: string): Promise<Company> {

    try {

      const result = await this.repo.findOne({
        where: { ID: companyID },
        relations: { Address: true }
      })

      return result as Company;

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }


  }

}

