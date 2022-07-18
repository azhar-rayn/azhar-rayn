// export all utils here
import bcrypt from 'bcrypt';
import { APP_SECRET } from '../config';

export const GeneratePassword = async (password: any) => {
  return await bcrypt.hash(password, 10);
};


export const ValidatePassword = async (enteredPassword: any, savedPassword: any) => {
  return await bcrypt.compare(enteredPassword, savedPassword);
};

export const GenerateTime = (time: string) => {
  if (!time)
    return undefined

  return (new Date(2000, 1, 1, parseInt(time.split(':')[0]), parseInt(time.split(':')[1])))
}
