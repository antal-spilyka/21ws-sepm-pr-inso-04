export enum HallplanElementType {
  seat = 'seat',
  hallway = 'hallway',
  stage = 'stage',
  exit = 'exit',
}

export interface IHallplanElement {
  added: boolean;
  removeCandidate: boolean;
  type: HallplanElementType;
  withType: (type: HallplanElementType) => IHallplanElement;
  withRemoveCandidate: (removeCandidate: boolean) => IHallplanElement;
  withAdded: (added: boolean) => IHallplanElement;
}

export class HallplanElement implements IHallplanElement {
  added = false;
  removeCandidate = false;
  type = HallplanElementType.seat;

  constructor(added?) {
    this.withRemoveCandidate = this.withRemoveCandidate.bind(this);
    this.withType = this.withType.bind(this);
    this.withAdded = this.withAdded.bind(this);
    if (added) {
      this.added = added;
    }
  }

  withRemoveCandidate(removeCandidate: boolean): HallplanElement {
    this.removeCandidate = removeCandidate;
    return this;
  }

  withType(type: HallplanElementType): HallplanElement {
    this.type = type;
    return this;
  }

  withAdded(added: boolean): HallplanElement {
    this.added = added;
    return this;
  }
}
