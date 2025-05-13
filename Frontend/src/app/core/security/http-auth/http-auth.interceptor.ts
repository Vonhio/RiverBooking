import { HttpInterceptorFn } from '@angular/common/http';

export const HttpAuthInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  let reqConToken = req;

  if (token) {
    reqConToken = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(reqConToken);
};
