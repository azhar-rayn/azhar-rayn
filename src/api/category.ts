import { Router } from 'express'
import { CategoryService } from '../services';

const CategoryRouter = Router();
const service: CategoryService = new CategoryService();

CategoryRouter.get('/list', async (req, res) => {
  const result = await service.ListCategory()
  res.json(result)
})

CategoryRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddCategory(name)
  res.json(result)
})


export default CategoryRouter;
