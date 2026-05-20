import { ScreeningCategory } from '../models/nirogi.models';

const MONTHS_IN_CAT1 = 6;

export function categoryFromAge(age: number): ScreeningCategory {
  if (age < 1) {
    return 'CAT1';
  }

  if (age < 8) {
    return 'CAT2';
  }

  if (age < 18) {
    return 'CAT3';
  }

  if (age < 40) {
    return 'CAT4';
  }

  if (age < 60) {
    return 'CAT5';
  }

  return 'CAT6';
}

export function calculateAgeInMonths(dateOfBirth: string, asOf = new Date()): number {
  const dob = new Date(dateOfBirth);
  if (Number.isNaN(dob.valueOf())) {
    return 0;
  }

  let months = (asOf.getFullYear() - dob.getFullYear()) * 12 + (asOf.getMonth() - dob.getMonth());

  if (asOf.getDate() < dob.getDate()) {
    months -= 1;
  }

  return Math.max(months, 0);
}

export function calculateAgeInYears(dateOfBirth: string, asOf = new Date()): number {
  return Math.floor(calculateAgeInMonths(dateOfBirth, asOf) / 12);
}

export function categoryFromDateOfBirth(dateOfBirth: string, asOf = new Date()): ScreeningCategory {
  const ageInMonths = calculateAgeInMonths(dateOfBirth, asOf);

  if (ageInMonths < MONTHS_IN_CAT1) {
    return 'CAT1';
  }

  return categoryFromAge(Math.floor(ageInMonths / 12));
}

export function ageDisplayFromDateOfBirth(dateOfBirth: string, asOf = new Date()): string {
  const ageInMonths = calculateAgeInMonths(dateOfBirth, asOf);

  if (ageInMonths < 24) {
    return `${ageInMonths} month${ageInMonths === 1 ? '' : 's'}`;
  }

  const years = Math.floor(ageInMonths / 12);
  const months = ageInMonths % 12;
  return months ? `${years} yrs ${months} mos` : `${years} yrs`;
}

export function wasScreenedWithinTwoYears(lastScreeningDate?: string): boolean {
  if (!lastScreeningDate) {
    return false;
  }

  const last = new Date(lastScreeningDate);
  if (Number.isNaN(last.valueOf())) {
    return false;
  }

  const twoYearsAgo = new Date();
  twoYearsAgo.setFullYear(twoYearsAgo.getFullYear() - 2);
  return last > twoYearsAgo;
}
