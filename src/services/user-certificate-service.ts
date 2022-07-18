// All Business logic will be here

import { Certificate } from "../entity";
import { UserCertificateRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class UserCertificateService {

  private repository: UserCertificateRepository

  constructor() {

    this.repository = new UserCertificateRepository();

  }


  //#######################################################################
  async GetCertificate(certificateID: string): Promise<Certificate> {

    try {

      return await this.repository.GetCertificate(certificateID);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async DeleteCertificate(certificateID: string): Promise<Certificate> {

    try {

      return await this.repository.DeleteCertificate(certificateID);

    }
    catch (err) {
     
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }

  //#######################################################################
  async UpdateCertificate(certificateID: string, topic: string, type: string, instituation: string, date: Date): Promise<Certificate> {

    try {

      return await this.repository.UpdateCertificate(certificateID, topic, type, instituation, date);

    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }

  }


}