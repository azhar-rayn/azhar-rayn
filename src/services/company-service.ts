import { Address, Company } from "../entity";
import { AddressRepository, CompanyRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class CompanyService {

  service: CompanyRepository
  address: AddressRepository

  constructor() {
    this.service = new CompanyRepository()
    this.address = new AddressRepository()

  }

  //#######################################################################
  async AddCompany(
    name: string,
    industryID: string,
    email: string,
    contactPersonName: string,
    jobDesignation: string,
    phone: number,
    logo: string,
    address: Address
  ): Promise<Company> {

    try {

      const { ID } = await this.address.AddAddress(address)

      return await this.service.AddCompany(name, industryID, ID, email, contactPersonName, jobDesignation, phone, logo);

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
    logo: string,
    address: Address
  ): Promise<Company> {

    try {

      await this.address.UpdateAddress(address)

      return await this.service.UpdateCompany(companyID, name, industryID, email, contactPersonName, jobDesignation, phone, logo);

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetCompany(companyID: string): Promise<Company> {

    try {

      return await this.service.GetCompany(companyID);

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }


  }

}

