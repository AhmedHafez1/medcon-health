export enum Day {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY,
}

export const DAYS: { value: number; label: string }[] = [
  { value: Day.MONDAY, label: 'Monday' },
  { value: Day.TUESDAY, label: 'Tuesday' },
  { value: Day.WEDNESDAY, label: 'Wednesday' },
  { value: Day.THURSDAY, label: 'Thursday' },
  { value: Day.FRIDAY, label: 'Friday' },
  { value: Day.SATURDAY, label: 'Saturday' },
  { value: Day.SUNDAY, label: 'Sunday' },
];
