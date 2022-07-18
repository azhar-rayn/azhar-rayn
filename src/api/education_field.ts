import { Router } from 'express'
import { EducationFieldService } from '../services';

const EducationFieldRouter = Router();
const service: EducationFieldService = new EducationFieldService();

EducationFieldRouter.get('/list', async (req, res) => {
  const result = await service.ListEducationField()
  res.json(result)
})

EducationFieldRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddEducationField(name)
  res.json(result)
})


export default EducationFieldRouter;
