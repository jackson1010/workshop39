export interface CharacterList {
  characters: {
    id: number;
    name: string;
    thumbnail: {
      path: string;
      extension: string;
    };
    comment: {
      comment: string;
      timeStamp: string;
    }[];
  }[];
}

export interface Character {
  id: number;
  name: string;
  thumbnail: {
    path: string;
    extension: string;
  };
  comment: {
    comment: string;
    timeStamp: string;
  }[];
}
