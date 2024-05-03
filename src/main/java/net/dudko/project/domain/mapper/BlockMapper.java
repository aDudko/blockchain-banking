package net.dudko.project.domain.mapper;

import net.dudko.project.domain.entity.blockchain.Block;
import net.dudko.project.model.dto.BlockDto;

public class BlockMapper {

    public static Block mapToBlock(BlockDto blockDto) {
        return Block.builder()
                .id(blockDto.getId())
                .hash(blockDto.getHash())
                .prevHash(blockDto.getPrevHash())
                .data(blockDto.getData())
                .timeCreate(blockDto.getTimeCreate())
                .nonce(blockDto.getNonce())
                .build();
    }

    public static BlockDto mapToBlockDto(Block block) {
        return BlockDto.builder()
                .id(block.getId())
                .hash(block.getHash())
                .prevHash(block.getPrevHash())
                .data(block.getData())
                .timeCreate(block.getTimeCreate())
                .nonce(block.getNonce())
                .build();
    }

}
