import express from 'express';
import { PORT } from './config';
import expressApp from './express-app'

const StartServer = async () => {

  const app = express();
  await expressApp(app);

  app.listen(PORT, () => {
    console.log(`listening to port ${PORT}`);
  })
    .on('error', (err) => {
      console.log(err);
      process.exit();
    })
}


try{
  StartServer();
}
catch(e) {
  console.log(e)
}