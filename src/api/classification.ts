import { Router } from 'express'
import { ClassificationService } from '../services';

const ClassificationRouter = Router();
const service: ClassificationService = new ClassificationService();

ClassificationRouter.get('/list', async (req, res) => {
  const result = await service.ListClassification()
  res.json(result)
})

ClassificationRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddClassification(name)
  res.json(result)
})


export default ClassificationRouter;
