export const STATUS_CODES = {
  OK: 200,
  BAD_REQUEST: 400,
  UN_AUTHORISED: 403,
  NOT_FOUND: 404,
  INTERNAL_ERROR: 500,
}

export class AppError extends Error {

  name: string
  statusCode: any
  description: string | undefined
  isOperational: any
  errorStack: any
  logError: any

  constructor(name: string, statusCode: any, description: string | undefined, isOperational: any) {
    super(description);
    Object.setPrototypeOf(this, new.target.prototype);
    this.name = name;
    this.statusCode = statusCode;
    this.isOperational = isOperational
    Error.captureStackTrace(this);
  }
}

//api Specific Errors
export class APIError extends AppError {
  constructor(name: string, statusCode = STATUS_CODES.INTERNAL_ERROR, description = 'Internal Server Error', isOperational = true,) {
    super(name, statusCode, description, isOperational);
  }
}