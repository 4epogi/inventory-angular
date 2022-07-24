export class User {
    constructor(private token: string,
        public username: string,
        private refreshToken: string,
        public expiresAt: Date) {
    }

    // get token() {

    // }
}
