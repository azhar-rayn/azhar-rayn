import { Router } from 'express'
import { IndustryService } from '../services';

const IndustryRouter = Router();
const service: IndustryService = new IndustryService();

IndustryRouter.get('/list', async (req, res) => {
  const result = await service.ListIndustry()
  res.json(result)
})

IndustryRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddIndustry(name)
  res.json(result)
})


export default IndustryRouter;
