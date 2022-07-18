import { Router } from 'express'
import { CompanyService } from '../services';

const CompanyRouter = Router();
const service: CompanyService = new CompanyService();

CompanyRouter.get('/:companyID', async (req, res) => {
  const { companyID } = req.params
  const result = await service.GetCompany(companyID)
  res.json(result)
})

CompanyRouter.put('/:companyID', async (req, res) => {
  const { companyID } = req.params
  const { name, industryID, email, contactPersonName, jobDesignation, phone, logo, address } = req.body
  const result = await service.UpdateCompany(companyID, name, industryID, email, contactPersonName, jobDesignation, phone, logo, address)
  res.json(result)
})


CompanyRouter.post('/', async (req, res) => {
  const { name, industryID, email, contactPersonName, jobDesignation, phone, logo, address } = req.body
  const result = await service.AddCompany(name, industryID, email, contactPersonName, jobDesignation, phone, logo, address)
  res.json(result)
})

export default CompanyRouter;
