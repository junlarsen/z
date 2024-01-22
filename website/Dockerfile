FROM node:20-alpine AS base

FROM base AS builder
RUN apk add --no-cache libc6-compat
RUN apk update

WORKDIR /app
RUN yarn global add turbo pnpm
COPY . .
RUN turbo prune --scope=@pivot/website --docker

COPY .gitignore .gitignore

RUN pnpm install --frozen-lockfile
RUN pnpm turbo build --filter=@pivot/website

FROM base AS runner
WORKDIR /app

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs
USER nextjs

COPY --from=builder /app/website/next.config.js .
COPY --from=builder /app/website/package.json .

COPY --from=builder --chown=nextjs:nodejs /app/website/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/website/.next/static ./website/.next/static
COPY --from=builder --chown=nextjs:nodejs /app/website/public ./website/public

CMD node website/server.js
