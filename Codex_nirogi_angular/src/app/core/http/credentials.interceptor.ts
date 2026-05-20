import { HttpInterceptorFn } from '@angular/common/http';

function readCookie(name: string): string | null {
  return document.cookie
    .split(';')
    .map((cookie) => cookie.trim())
    .find((cookie) => cookie.startsWith(`${name}=`))
    ?.split('=')
    .slice(1)
    .join('=') ?? null;
}

export const credentialsInterceptor: HttpInterceptorFn = (request, next) => {
  const xsrfToken = readCookie('XSRF-TOKEN');
  const headers = xsrfToken
    ? request.headers.set('X-XSRF-TOKEN', decodeURIComponent(xsrfToken))
    : request.headers;

  return next(
    request.clone({
      headers: headers.set('X-Requested-With', 'XMLHttpRequest'),
      withCredentials: true,
    }),
  );
};
